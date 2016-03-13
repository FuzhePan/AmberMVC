package AmberMVC;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * Created by FuzhePan on 2016/3/12.
 */
public abstract class BaseController {

    protected String templateBasePath = "views/" ;

    /**
     * 执行view
     * @param templatePath
     * @param viewModelName
     * @param viewModel
     * @return
     */
    protected String view(String templatePath,String viewModelName,Object viewModel){

        templatePath = templateBasePath+templatePath;

        //todo:判断模板是否存在
        Template template = Velocity.getTemplate(templatePath);

        VelocityContext velocityContext = new VelocityContext();
        if(viewModel!=null){
            velocityContext.put(viewModelName,viewModel);
        }

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext,stringWriter);

        return stringWriter.toString();

    }

    protected  String view(String templatePath, Object viewModel){
        return view(templatePath, "model", viewModel);
    }

    protected  String view(String templatePath){
        return view(templatePath,null);
    }

    /**
     * todo: json
     * @return
     */
    protected String json(){
        return "{}";
    }

}
