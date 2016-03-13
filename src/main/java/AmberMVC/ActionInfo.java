package AmberMVC;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by FuzhePan on 2016/3/12.
 */
public class ActionInfo {

    private Method method;
//    public Method getMethod(){return method;}
  //  public void setMethod(Method method ){this.method = method;}

    private Object controllerObject;
    /*public void setControllerObject(Object controllerObject){
        this.controllerObject = controllerObject;
    }
*/
    public Object execute() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(controllerObject);
    }

    public ActionInfo(Object controllerObject,Method method){
        this.controllerObject = controllerObject;
        this.method = method;
    }


}
