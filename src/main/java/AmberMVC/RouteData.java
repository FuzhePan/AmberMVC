package AmberMVC;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FuzhePan on 2016/3/12.
 */
public class RouteData {

    private Map<String,ActionInfo> actionInfoMap = new HashMap<String, ActionInfo>();

    public void addActionInfo(String path,ActionInfo actionInfo){
        actionInfoMap.put(path,actionInfo);
    }

    public void addActionInfo(String path,Object controllerObject,Method method){
        ActionInfo actionInfo = new ActionInfo(controllerObject,method);
        addActionInfo(path,actionInfo);
    }

    public ActionInfo getActionInfo(String path){
        if(actionInfoMap.containsKey(path)){
            return actionInfoMap.get(path);
        }
        return null;
    }

    private RouteData(){}

    private static RouteData routeData = null;

    /**
     * µ¥Àý
     * @return
     */
    public static synchronized RouteData getInstance(){
        if(routeData==null){
            routeData = new RouteData();
        }
        return routeData;
    }
}
