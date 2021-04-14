package bg.softuni.hotelagency.interceptor;

import bg.softuni.hotelagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AvatarInterceptor implements HandlerInterceptor{

    @Autowired
    private UserService userService;


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !isRedirectView(modelAndView)) {
            if (isUserLogged()) {
                addToModelUserDetails(modelAndView);
            }
        }
    }

    private void addToModelUserDetails(ModelAndView model) {
//        log.info("=============== addToModelUserDetails =========================");

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        model.addObject("avatar", userService.getUserProfilePicture(username));

//        log.trace("session : " + model.getModel());
//        log.info("=============== addToModelUserDetails =========================");
    }

    public static boolean isRedirectView(ModelAndView mv) {
        String viewName = mv.getViewName();
        if (viewName.startsWith("redirect:/")) {
            return true;
        }
        View view = mv.getView();
        return (view instanceof SmartView
                && ((SmartView) view).isRedirectView());
    }

    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication()
                    .getName().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }
}
