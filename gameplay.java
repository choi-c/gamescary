/**
* This class is used for a variety of gameplay mechanics
*/
public class gameplay extends scary{
    // Properties
    boolean blnAlive = true;
    int intH1X,intH1Y;
    int intS1X, intS1Y;
    int intH2X,intH2Y;

    // Methods

    /**
    * Used to determine power-up status during gameplay
    * @return boolean values depending on the power-up status
    */
    public boolean characterBuffs(){
         
        if(panel.intPX == panel.flx && panel.intPY == panel.fly){
            //character on flashlight powerup
            return true;
        }else if(panel.intPX == panel.ix && panel.intPY == panel.iy){
            //character on ice powerup
            return true;
        }
        /*
        // if seeker chooses eyeball skin
        if(panel.strSelect.equals("seeker1") || panel.blnPfl == true){
            //sees more blocks
            panel.blnVisibility = true;
        // if seeker chooses monster skin
        }else if(panel.strSelect.equals("seeker2")){
            //bigger tag radius (?)
        
        }else if(panel.blnPi == true){
            //ice powerup

        }*/
        return false;
    }
    /**
    * Determines whether the players are vulnerable to being tagged by the monster
    * @return boolean values depending on whether the player is able to be tagged
    */
    public boolean tag(int intH1X,int intH1Y,int intS1X,int intS1Y,int intH2X,int intH2Y,int intS2X, int intS2Y){
        if(intS1X == intH1X && intS1Y == intH1Y){
            return true;
        }else if (intS1X == intH2X && intS1Y == intH2Y){
            return true;
        }

        if(intS2X > (intH1X-1) && intS2Y < (intH1X+1) && intS2Y > (intH1Y-1) && intS2Y < (intH1Y+1)){
            return true;
        }else if(intS2X > (intH2X-1) && intS2Y < (intH2X+1) && intS2Y > (intH2Y-1) && intS2Y < (intH2Y+1)){
            return true;
        }
        return false;
    }
    /**
    * Controls the boundaries for all players
    * @return integer values based on where the player is
    */
    public int boundaries(String strBlock){
        if(strBlock == "w" || strBlock == "s" || strBlock == "c"){
            //2 == dont move
            return 2;
        }else if(strBlock == "l"){
            //3 == dont print character
            return 3;
        }else if(strBlock == "t"){
            return 3;
        }else if(strBlock == "f"){
            //1 == move freely
            return 1;
        }else{
            return 0;
        }
    }

    // Constructor
    public gameplay(){
    }
}
