public class Display {
   String name = "Olise";
   int goals = 100;
   boolean hasAura = true;

   public static void main(String[] args) {
    Display player = new Display();
    player.showInfo();
   }

   public void showInfo() {
    System.out.println("Player Name: " + this.name);
    System.out.println("Goals Scored: " + this.goals);
    System.out.println("Has Aura: " + this.hasAura);
   }
}
