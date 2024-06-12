public class gameplay extends scary{
    // Properties
    boolean blnAlive = true;
    int intH1X,intH1Y;
    int intS1X, intS1Y;
    int intH2X,intH2Y;

    // Methods
    public boolean characterBuffs(){
         
        if(panel.intPX == panel.flx && panel.intPY == panel.fly){
            //character on flashlight powerup
            return true;
            //panel.blnPfl = true;
            //System.out.println(panel.blnPfl);
        }else if(panel.intPX == panel.ix && panel.intPY == panel.iy){
            //character on ice powerup
            return true;
            //panel.blnPi = true;
            //System.out.println(panel.blnPi);
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

    // Constructor
    public gameplay(){
    }
}
