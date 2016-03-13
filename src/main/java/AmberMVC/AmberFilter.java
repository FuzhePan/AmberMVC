package AmberMVC;

/**
 * Created by FuzhePan on 2016/3/12.
 */
import org.apache.velocity.app.Velocity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * MVC框架的入口
 */
public class AmberFilter implements Filter {
    /**
     * AmberMVC 初始化
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();

        String websitePath = servletContext.getRealPath("/");
        Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,websitePath);
        Velocity.setProperty(Velocity.RESOURCE_LOADER, "file");
        Velocity.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        Velocity.setProperty("file.resource.loader.cache", "false");
        Velocity.setProperty("file.resource.loader.modificationCheckInterval", "2");
        Velocity.setProperty("input.encoding", "UTF-8");
        Velocity.setProperty("output.encoding", "UTF-8");
        Velocity.setProperty("default.contentType", "text/html; charset=UTF-8");
        Velocity.setProperty("velocimarco.library.autoreload", "true");
        Velocity.setProperty("runtime.log.error.stacktrace", "false");
        Velocity.setProperty("runtime.log.warn.stacktrace", "false");
        Velocity.setProperty("runtime.log.info.stacktrace", "false");
        Velocity.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        Velocity.setProperty("runtime.log.logsystem.log4j.category", "velocity_log");
        Velocity.init();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        //获取请求的path
        String uri = httpServletRequest.getRequestURI();
        int n = uri.indexOf('?');
        if (n!=(-1))
            uri = uri.substring(0, n);
        n = uri.indexOf('#');
        if (n!=(-1))
            uri = uri.substring(0, n);

        ActionInfo actionInfo = RouteData.getInstance().getActionInfo(uri);
        if(actionInfo == null){
            returnMessage(servletResponse,"没有找到对应的action，uri="+uri);
        }

        //执行action
        Object actionResult = null;
        try {
            actionResult = actionInfo.execute();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            returnMessage(servletResponse, "执行action时，遇到InvocationTargetException");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            returnMessage(servletResponse, "执行action时，遇到IllegalAccessException");
        }

        //返回结果
        if(actionResult == null){returnMessage(servletResponse,"action 没有返回任何结果") ;}
        if(actionResult instanceof String
                || actionResult instanceof Integer
                || actionResult instanceof Long
                || actionResult instanceof Double){
            returnMessage(servletResponse,actionResult.toString());
        }
        if(actionResult instanceof ActionResult){
            //todo：执行actionResult
        }
    }

    public void destroy() {
    }

    /**
     * 输出信息
     * @param servletResponse
     * @param message
     * @throws IOException
     */
    private void returnMessage(ServletResponse servletResponse,String message) throws IOException {
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html");

        PrintWriter printWriter = servletResponse.getWriter();
        printWriter.println(message);
        printWriter.flush();
        printWriter.close();
    }
}
