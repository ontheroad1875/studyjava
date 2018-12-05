package strategy_pattern;

public class Context {
    Weapon weapon;
    
    public Context(Weapon weapon) { //构造函数
        super();
        this.weapon = weapon;
    }
    
    public Weapon getWeapon() { //get方法
        return weapon;
    }
    
    public void setWeapon(Weapon weapon) { //set方法
        this.weapon = weapon;
    }
    
    public void gun() {
        weapon.gun();
    }
}