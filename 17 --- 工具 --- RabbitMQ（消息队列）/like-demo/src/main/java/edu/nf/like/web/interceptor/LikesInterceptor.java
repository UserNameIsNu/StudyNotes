package edu.nf.like.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nf.like.common.result.ResultData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author wangl
 * @date 2023/12/8
 */
public class LikesInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null) {
            responseView(response);
            return false;
        }
        return true;
    }

    private void responseView(HttpServletResponse response) throws Exception {
        ResultData<?> vo = new ResultData<>();
        vo.setCode(HttpStatus.UNAUTHORIZED.value());
        String json = new ObjectMapper().writeValueAsString(vo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);
    }
}