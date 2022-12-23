package org.phoenixframework.liveview.managers

import android.util.Log
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.phoenixframework.Channel
import org.phoenixframework.Message
import org.phoenixframework.Payload
import org.phoenixframework.Socket
import org.phoenixframework.liveview.data.dto.ComposableNodeFactory
import org.phoenixframework.liveview.data.dto.ComposableTreeNode
import org.phoenixframework.liveview.mappers.SocketPayloadMapper
import org.phoenixframework.liveview.ui.phx_components.PhxAction
import java.net.ConnectException
import java.util.*

class SocketManager(
    private val okHttpClient: OkHttpClient,
    private val socketPayloadMapper: SocketPayloadMapper
) {

    private val androidTemplate = """
       
        <card elevation="8" shape="8" size="300" background-color = "0xFFF2F2F2" >
        <column vertical-arrangement="space-evenly" horizontal-alignment="start" width="280" width="100" padding = "12" >
         <async-image width="fill" height="150" content-scale="fill-bounds"> https://www.themoviedb.org/t/p/w1280/94xxm5701CzOdJdUEdIuwqZaowx.jpg </async-image>
         <text color="0xFF000000" font-size="24" font-weight="W800"> Avatar- way of the water </text>
          <text color="0xFF000000" font-size="14" max-lines="4" overflow="ellipsis"> It’s a James Cameron film, so it’s impressive. The special effects, camerawork, world-building, and action were all off the charts. But Avatar: The Way of Water struggles like its predecessor in the story and character development departments. In fact, the story of The Way of Water is almost identical to the first Avatar. Instead of humans learning to be Na’vi and then fighting Stephen slang, a family of forest Na’vi learns to be ocean Na’vi and then fight Stephen Lang. </text>
          <row horizontal-arrangement="space-between" vertical-alignment="center">
             <text color="0xFFF4B400" font-size="12"> Audience rating 94%</text>
             <text color="0xFFF4B400" font-size="12"> Roton tomatoes rating 64%</text>
           </row>
         </column>
         </card>
        
    """
    private var phxSocket: Socket? = null
    private var channel : Channel? = null
    private val uuid: String = UUID.randomUUID().toString()

    private var liveReloadChannel: Channel? = null
    private var liveReloadSocket: Socket? = null

    lateinit var domParsedListener: (Document) -> Unit
    var liveReloadListener: (() -> Unit)? = null

    var nodeListener :  (node: ComposableTreeNode) -> Unit = {

    }

    fun connectToChatRoomWithParams(
        phxLiveViewPayload: PhoenixLiveViewPayload
    ) {

        liveReloadSocket?.disconnect()
        phxSocket?.disconnect()

        Log.d("TAG", "Connection to socket with params $phxLiveViewPayload")
        // Create the Socket
        val socketParams = mapOf(
            "_csrf_token" to phxLiveViewPayload._csrfToken,
            "_mounts" to 0,
            "client_id" to uuid,
            "_platform" to "android"
        )

        val socketQueryParams = socketParams.entries.fold("") { acc: String, entry: Map.Entry<String, Any?> ->
            acc + "${entry.key}=${entry.value}&"
        }

        phxSocket = Socket(
            url = "${LiveViewState.baseSocketUrl}/live/websocket?$socketQueryParams",
            client = okHttpClient
        )

        liveReloadSocket = Socket(
            url = "${LiveViewState.baseSocketUrl}/phoenix/live_reload/socket",
            client = okHttpClient
        )

        // Listen to events on the Socket
        phxSocket?.logger = {
            Log.d("PHX-SOCKET LOGGER TAG", it)
        }

        phxSocket?.onError { error, response ->

            Log.e("ON ERROR", error.toString())
            Log.e("ON ERROR RESPONSE", error.toString())


        }


        phxSocket?.onOpen {
            Log.d("TAG", "----- SOCKET OPENED -----")
        }


        val channelConnectionParams = mapOf(
            "session" to phxLiveViewPayload.dataPhxSession,
            "static" to phxLiveViewPayload.dataPhxStatic,
            "url" to LiveViewState.baseUrl,
            "params" to mapOf(
                "_mounts" to 0,
                "_csrf_token" to phxLiveViewPayload._csrfToken,
                "_platform" to "android",
                "client_id" to uuid
            )
        )

        channel = phxSocket?.channel(
            topic = "lv:${phxLiveViewPayload.phxId}",
            params = channelConnectionParams
        )

        liveReloadChannel = liveReloadSocket?.channel(
            topic = "phoenix:live_reload"
        )

        channel?.join()
            ?.receive("ok") { theMessage: Message ->
                Log.d("SOCKET MANAGER CHANNEL JOIN OK", "CHAT ROOM LIVEVIEW JOINED")
                val payload: Map<String, Any?> = theMessage.payload
                val outputDom = socketPayloadMapper.mapRawPayloadToDom(payload)
                domParsedListener.invoke(outputDom)

            }
            ?.receive("error") {
                /* failed to join the chatroom */
                Log.d("SOCKET MANAGER CHANNEL JOIN ERROR", "CHAT ROOM LIVEVIEW ERROR")
                Log.e("ERROR", it.toString())
            }
            ?.receive("response") {
                Log.d("RESPONSE", "CHAT ROOM RESPONSE")
            }

        channel?.onMessage { message: Message ->
            Log.d("CHANNEL MESSAGE", message.toString())

            when(message.event) {
                "phx_reply" -> {
                    Log.d("ON MESSAGE PAYLOAD", message.payload.toString())

                    val outputDom: Document? = socketPayloadMapper.parseDiff(message)
                    outputDom?.let {
                        domParsedListener.invoke(it)
                    }


                }

                "diff" -> {
                    Log.d("ON DIFF PAYLOAD", message.payload.toString())

                    socketPayloadMapper.extractDiff(message.payload)
                }
            }

            message
        }

        liveReloadChannel?.join()?.receive("ok") { theLiveReloadMessage ->
            Log.d("LIVE RELOAD CHANNEL", theLiveReloadMessage.toString())
        }

        liveReloadChannel?.onMessage { theLiveReloadMessage ->
            when (theLiveReloadMessage.event) {
                "assets_change" -> {
                    Log.d("LIVE RELOAD CHANNEL MESSAGE", theLiveReloadMessage.payload.toString())
                    liveReloadListener?.invoke()
                }
                else -> {

                }
            }
            theLiveReloadMessage
        }

        phxSocket?.onClose { Log.d("TAG", "Socket Closed") }
        phxSocket?.onError { throwable, response ->

            Log.e("TAG", throwable.message.toString())

            when(throwable) {
                is ConnectException -> {
                    val errorHtml = "<column>" +
                            "<text width=fill padding=16>" + throwable.message.toString() + "</text>" +
                            "<text width=fill padding=16>" + "This probably means your localhost server isn't running...\nPlease start your server in the terminal using iex -S mix phx.server and rerun the android application" + "</text>" +
                            "</column>"
                    val errorDomState = Jsoup.parse(errorHtml)
                    domParsedListener.invoke(errorDomState)

                }

                else -> {
                    val errorHtml = "<column>" +
                            "<text width=fill padding=16>" + throwable.message.toString() + "</text>" +
                            "</column>"
                    val errorDomState = Jsoup.parse(errorHtml)
                    domParsedListener.invoke(errorDomState)
                }
            }
        }

        phxSocket?.connect()
        liveReloadSocket?.connect()
    }


    fun parseTemplate(){
        val elements = Jsoup.parse(androidTemplate)
            .apply { ownerDocument()!!.outputSettings().prettyPrint(false) }.body().children()


        elements.forEach { element ->
            val root = createComposable(element)
            extractChildren(root, element.children())
            nodeListener(root)
        }

    }

    // Method to recursively add child nodes to the tree
    private fun extractChildren(parent: ComposableTreeNode, children: Elements) {
        for (child in children) {
            // Create a tree node for the child element
            val childNode = createComposable(child)

            // Add the child node to the parent node
            parent.addNode(childNode)

            // Recursively add the child's child nodes to the tree
            extractChildren(childNode, child.children())
        }
    }

    private fun createComposable(element: Element): ComposableTreeNode {

        return ComposableNodeFactory.buildComposable(element)


    }

    fun pushChannelMessage(phxAction: PhxAction) {
        pushChannelMessage(event = phxAction.event, payload = phxAction.payload)
    }

    private fun pushChannelMessage(event: String, payload: Payload) {
        channel?.push(
            event = event,
            payload = payload
        )
    }

}


