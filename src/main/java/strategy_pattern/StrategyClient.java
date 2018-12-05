package strategy_pattern;

public class StrategyClient {

    public static void main(String[] args) {
        //使用构造函数默认选择一把AWM狙击步枪（一个策略）
        Context context=new Context(new FirstGun());
        context.gun();
        
        //使用get、set方法切换到S12K霰弹枪（切换策略）
        System.out.println("------右前方30米发现敌人------");
        context.setWeapon(new SecondGun());
        context.gun();
    }
}