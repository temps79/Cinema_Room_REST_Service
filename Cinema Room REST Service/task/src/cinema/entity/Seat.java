package cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public  class Seat {
    private final int row;
    private final int column;
    private final int price;

    private boolean isActive;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8;
        this.isActive=true;
    }

    public Seat() {
        row=0;
        column=0;
        price=0;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    @JsonIgnore
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
