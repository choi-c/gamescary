public class gameplay extends scary{
    // Properties
    boolean blnVisibility = false;
    boolean blnPi = false;
    boolean blnPfl = false;
    boolean blnAlive = true;
    int intH1X,intH1Y;
    int intS1X, intS1Y;
    int intH2X,intH2Y;

    // Methods
    public void characterBuffs(int intChrChoice,boolean blnVisibility){
        /* 
        if(intPX == flx && intPY == fly){
            //character on flashlight powerup
            blnPfl == true;
        }else if(intPX == ix && intPY == iy){
            //character on ice powerup
            blnPi == true
        }*/

        // if seeker chooses eyeball skin
        if(panel.strSelect.equals("seeker1") || blnPfl == true){
            //sees more blocks
            blnVisibility = true;
        // if seeker chooses monster skin
        }else if(panel.strSelect.equals("seeker2")){
            //bigger tag radius (?)
        
        }else if(blnPi == true){
            //ice powerup

        }
    }
    public boolean tag(int intChrChoice,int intH1X,int intH1Y,int intS1X,int intS1,int intH2X,int intH2Y){
        if(intS1X == intH1X && intS1Y == intH1Y){
            return true;
        }else if (intS1X == intH2X && intS1Y == intH2Y){
            return true;
        }
        return false;
    }
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
    // printing different character skin depending on character selected
    public int chrChoice(){
        int intChoice = 0;
        if(panel.strSelect.equals("seeker1")){
            intChoice = 1;
        }
        return intChoice;
    }

    // Constructor
    public gameplay(){
    }
}
