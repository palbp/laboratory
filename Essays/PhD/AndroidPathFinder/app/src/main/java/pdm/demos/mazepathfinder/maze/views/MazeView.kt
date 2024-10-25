package pdm.demos.mazepathfinder.maze.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pdm.demos.mazepathfinder.R
import pdm.demos.mazepathfinder.domain.Maze
import pdm.demos.mazepathfinder.domain.Maze.Cell
import pdm.demos.mazepathfinder.domain.buildTheSampleMaze
import pdm.demos.mazepathfinder.ui.theme.MazePathFinderTheme

const val WALL_WIDTH = 12f

@Composable
fun MazeView(maze: Maze, highlighted: List<Cell>, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(1f)
            .aspectRatio(0.8f)
    ) {
        repeat(maze.rowCount) { rowIndex ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(weight = 1.0f, fill = true)
            ) {
                val row = maze[rowIndex]
                repeat(row.size) { columnIndex ->
                    MazeCellView(
                        cell = row[columnIndex],
                        isVisited = highlighted.any { it.row == rowIndex && it.column == columnIndex },
                        modifier = Modifier.weight(weight = 1.0f, fill = true)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MazeViewPreview() {
    MazePathFinderTheme {
        val maze = buildTheSampleMaze()
        MazeView(maze = maze, listOf(Cell(4, 4), Cell(4, 3)))
    }
}

@Composable
fun MazeCellView(cell: Cell, isVisited: Boolean, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(
                color = if (isVisited) Color.LightGray else Color.White,
            )
            .drawWithContent {
                drawContent()
                drawLine(
                    Black,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = size.width, y = 0f),
                    strokeWidth = if (Maze.Wall.NORTH in cell.walls) WALL_WIDTH else Stroke.HairlineWidth
                )
                drawLine(
                    Black,
                    start = Offset(x = size.width, y = 0f),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = if (Maze.Wall.EAST in cell.walls) WALL_WIDTH else Stroke.HairlineWidth
                )
                drawLine(
                    Black,
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = if (Maze.Wall.SOUTH in cell.walls) WALL_WIDTH else Stroke.HairlineWidth
                )
                drawLine(
                    Black,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = size.height),
                    strokeWidth = if (Maze.Wall.WEST in cell.walls) WALL_WIDTH else Stroke.HairlineWidth
                )
            }
    ) {
        when (cell.annotation) {
            Maze.Annotation.START -> Text(
                text = stringResource(R.string.maze_start_label),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Blue,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(4.dp)
            )

            Maze.Annotation.GOAL -> Text(
                text = stringResource(R.string.maze_goal_label),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(4.dp)
            )

            else -> {}
        }

        cell.label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
fun MazeCellViewPreview() {
    MazePathFinderTheme {
        MazeCellView(
            cell = Cell(
                row = 0,
                column = 0,
                label = "A",
                walls = setOf(Maze.Wall.NORTH, Maze.Wall.WEST),
                annotation = Maze.Annotation.START,
            ),
            isVisited = true,
            modifier = Modifier
                .size(100.dp)
                .aspectRatio(1f)
        )
    }
}