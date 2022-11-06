package src.uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import src.uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected int animate;

    protected boolean appear;

    protected boolean passable;

    protected boolean destroyable;

    protected int status;

    public Entity(){}
    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.animate = 0;
        this.appear = true;
        this.passable = false;
        this.destroyable = false;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getX() {
        return x / Sprite.SCALED_SIZE;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y / Sprite.SCALED_SIZE;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public boolean intersects(Entity e) {
        return e.getBoundary().intersects(this.getBoundary());
    }

    public boolean checkAppearance() { return appear; }

    public boolean canPass(){return passable;}
    public boolean canDestroy() {return destroyable; }

    public void destroy() { appear = false;}

    public int getStatus(){return status;}

    public void setStatus(int s){status = s;}

    public boolean inCell()
    {
        return (this.getX() * Sprite.SCALED_SIZE == x)
                &&(this.getY() * Sprite.SCALED_SIZE == y);
    }
    public void updateStatus(){
        if(status == 0){
            status = 1;
            animate = 0;
        }
    }
}
