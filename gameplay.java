public class gameplay extends scary{
    // Properties
    boolean blnVisibility = false;
    boolean blnAlive = true;
    int intChrChoice = 0;
    int intH1X,intH1Y;
    int intS1X, intS1Y;
    int intH2X,intH2Y;

    // Methods
    public void characterBuffs(int intChrChoice,boolean blnVisibility){
        // if seeker chooses eyeball skin
        if(intChrChoice == 1){
            blnVisibility = true;
        // if seeker chooses monester skin
        }else if(intChrChoice == 2){

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
    public int boundaries(){
        String strBlock = strMap[intPX][intPY];
        if(strBlock == "w"){
            //2 == dont move
            return 2;
        }else if(strBlock == "s"){
            return 2;
        }else if(strBlock == "l"){
            //3 == dont print character
            return 3;
        }else if(strBlock == "t"){
            return 3;
        }else if(strBlock == "f"){
            //1 == move freely
            return 1;
        }
    }

    // Constructor
    public gameplay(){
    }
}
