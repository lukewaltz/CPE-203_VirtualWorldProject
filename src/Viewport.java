package src;

import processing.core.PImage;

import java.util.Optional;

public final class Viewport {
    private int row;
    private int col;
    private final int numRows;
    private final int numCols;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public boolean contains(Point p) {
        return p.y >= this.row && p.y < this.row + this.numRows && p.x >= this.col && p.x < this.col + this.numCols;
    }

    private void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public Point worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }

    public Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    private static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

    public void shiftView(int colDelta, int rowDelta, WorldView worldView) {
        int newCol = clamp(col + colDelta, 0, worldView.world.numCols - numCols);
        int newRow = clamp(row + rowDelta, 0, worldView.world.numRows - numRows);

        shift(newCol, newRow);
    }

    public void drawBackground(WorldView worldView) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Point worldPoint = viewportToWorld(col, row);
                Optional<PImage> image = worldView.world.getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    worldView.screen.image(image.get(), col * worldView.tileWidth, row * worldView.tileHeight);
                }
            }
        }
    }
}
