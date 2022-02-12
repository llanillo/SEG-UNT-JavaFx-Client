package com.seg.viewcontainer.configuration;

public enum Path {    

    // Home view
    LOGIN_CONTAINER ("login/Login-Container"),
    LOGIN ("login/Login"),
    REGISTER ("login/Register"),

    // Commission View
    COMMISSION_TABLE ("principal/commission/CommissionTable"),
    ADD_FILE ("principal/commission/AddDocument"),
    SEE_FILE (""),
    MANAGEMENT_TABLE ("principal/commission/ManagementTable"),    
    SEE_FILE_MANAGER (""),        

    // Principal View
    PRINCIPAL ("principal/Principal"), 
    WELCOME ("principal/Welcome"),    

    // Account View  
    ACCOUNT (""),
    MY_DOCUMENTS(""),
    CONFIGURATION (""),

    // Administrators View
    MANAGER_TABLE (""),
    ADD_MANAGER (""),
    RECORD (""),
    USER_TABLE (""),     
    SEE_USER (""),
    
    // Menu View
    COMMISSION_MENU ("principal/menu/CommissionMenu"),
    ADMIN_MENU("principal/menu/AdminMenu"),
    ACCOUNT_MENU ("principal/menu/AccountMenu"),

    // Secondary View
    EDITION_BOX (""),
    PROGRESS_BAR (""),
    DRAG_AND_DROP ("secondary/commission/DragAndDrop"),
    INFINITE_LOAD ("secondary/general/Load");
    
    private final String path;

    private Path(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "/com/seg/fxml/" + path + ".fxml";
    }                
}
