package model;

public class Point {
    private int width;
    private int height;

    public Point(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCoordinates(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void add(Point point) {
        this.width += point.width;
        this.height += point.height;
    }

    public int getOptimalDistance(Point point) {
        int widthDifference = Math.abs(width - point.getWidth());
        int heighDifference = Math.abs(height - point.getHeight());
        return Math.max(widthDifference, heighDifference);
    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        return point.getWidth() == width && point.getHeight() == height;
    }
}
