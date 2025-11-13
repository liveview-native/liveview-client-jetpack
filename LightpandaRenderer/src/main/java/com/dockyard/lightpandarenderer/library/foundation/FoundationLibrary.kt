package com.dockyard.lightpandarenderer.library.foundation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.dockyard.lightpandarenderer.NodeComposable
import com.dockyard.lightpandarenderer.library.ElementLibrary
import com.dockyard.lightpandarenderer.library.attributeValue

fun ElementLibrary.Companion.registerFoundation() {
    // Layout Components
    register("row") { node ->
        Row(
            modifier = node.modifier(),
            horizontalArrangement = node.attributeValue<Arrangement.Horizontal>("horizontalArrangement") ?: Arrangement.Start,
            verticalAlignment = node.attributeValue<Alignment.Vertical>("verticalAlignment") ?: Alignment.Top
        ) {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    register("column") { node ->
        Column(
            modifier = node.modifier(),
            verticalArrangement = node.attributeValue<Arrangement.Vertical>("verticalArrangement") ?: Arrangement.Top,
            horizontalAlignment = node.attributeValue<Alignment.Horizontal>("horizontalAlignment") ?: Alignment.Start
        ) {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    register("box") { node ->
        Box(
            modifier = node.modifier(),
            contentAlignment = node.attributeValue<Alignment>("contentAlignment") ?: Alignment.TopStart
        ) {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    register("spacer") { node ->
        Spacer(modifier = node.modifier())
    }

    // Text Components
    register("basictext") { node ->
        BasicText(
            text = node.attributeValue<String>("text") ?: "",
            modifier = node.modifier()
        )
    }

    register("basictextfield") { node ->
        BasicTextField(
            value = node.attributeValue<String>("value") ?: "",
            onValueChange = { /* Handle state */ },
            modifier = node.modifier()
        )
    }

    // Lazy Lists
    register("lazycolumn") { node ->
        LazyColumn(
            modifier = node.modifier(),
            verticalArrangement = node.attributeValue<Arrangement.Vertical>("verticalArrangement") ?: Arrangement.Top,
            horizontalAlignment = node.attributeValue<Alignment.Horizontal>("horizontalAlignment") ?: Alignment.Start
        ) {
            items(node.children.size) { index ->
                NodeComposable(node.children[index])
            }
        }
    }

    register("lazyrow") { node ->
        LazyRow(
            modifier = node.modifier(),
            horizontalArrangement = node.attributeValue<Arrangement.Horizontal>("horizontalArrangement") ?: Arrangement.Start,
            verticalAlignment = node.attributeValue<Alignment.Vertical>("verticalAlignment") ?: Alignment.Top
        ) {
            items(node.children.size) { index ->
                NodeComposable(node.children[index])
            }
        }
    }

    // Lazy Grids
    register("lazyverticalgrid") { node ->
        val columns = node.attributeValue<Int>("columns") ?: 2
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = node.modifier(),
            verticalArrangement = node.attributeValue<Arrangement.Vertical>("verticalArrangement") ?: Arrangement.Top,
            horizontalArrangement = node.attributeValue<Arrangement.Horizontal>("horizontalArrangement") ?: Arrangement.Start
        ) {
            items(node.children.size) { index ->
                NodeComposable(node.children[index])
            }
        }
    }

    register("lazyhorizontalgrid") { node ->
        val rows = node.attributeValue<Int>("rows") ?: 2
        LazyHorizontalGrid(
            rows = GridCells.Fixed(rows),
            modifier = node.modifier(),
            horizontalArrangement = node.attributeValue<Arrangement.Horizontal>("horizontalArrangement") ?: Arrangement.Start,
            verticalArrangement = node.attributeValue<Arrangement.Vertical>("verticalArrangement") ?: Arrangement.Top
        ) {
            items(node.children.size) { index ->
                NodeComposable(node.children[index])
            }
        }
    }

    // Lazy Staggered Grids
    register("lazyverticalstaggeredgrid") { node ->
        val columns = node.attributeValue<Int>("columns") ?: 2
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(columns),
            modifier = node.modifier(),
            verticalItemSpacing = node.attributeValue<Int>("verticalItemSpacing")?.dp ?: 0.dp,
            horizontalArrangement = node.attributeValue<Arrangement.Horizontal>("horizontalArrangement") ?: Arrangement.Start
        ) {
            items(node.children.size) { index ->
                NodeComposable(node.children[index])
            }
        }
    }

    register("lazyhorizontalstaggeredgrid") { node ->
        val rows = node.attributeValue<Int>("rows") ?: 2
        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(rows),
            modifier = node.modifier(),
            horizontalItemSpacing = node.attributeValue<Int>("horizontalItemSpacing")?.dp ?: 0.dp,
            verticalArrangement = node.attributeValue<Arrangement.Vertical>("verticalArrangement") ?: Arrangement.Top
        ) {
            items(node.children.size) { index ->
                NodeComposable(node.children[index])
            }
        }
    }

    // Pagers
    register("horizontalpager") { node ->
        HorizontalPager(
            state = rememberPagerState { node.children.size },
            modifier = node.modifier()
        ) { page ->
            NodeComposable(node.children[page])
        }
    }

    register("verticalpager") { node ->
        VerticalPager(
            state = rememberPagerState { node.children.size },
            modifier = node.modifier()
        ) { page ->
            NodeComposable(node.children[page])
        }
    }
}