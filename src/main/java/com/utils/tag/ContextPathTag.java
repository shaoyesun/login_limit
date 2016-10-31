package com.utils.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;


public class ContextPathTag extends TagSupport {

    private static final long serialVersionUID = -6970209213627366341L;

    public int doEndTag() throws javax.servlet.jsp.JspException {
        return EVAL_PAGE;
    }

    public int doStartTag() throws javax.servlet.jsp.JspException {
        try {
            pageContext.getOut().print(
                    ((HttpServletRequest) pageContext.getRequest())
                            .getContextPath());
        } catch (IOException ex) {
        }
        return SKIP_BODY;
    }
}
