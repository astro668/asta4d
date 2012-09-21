package com.astamuse.asta4d.test;

import com.astamuse.asta4d.Context;
import com.astamuse.asta4d.data.annotation.ContextData;
import com.astamuse.asta4d.render.Renderer;
import com.astamuse.asta4d.test.infra.BaseTest;
import com.astamuse.asta4d.test.infra.SimpleCase;

public class InjectTest extends BaseTest {

    public static class TestRender {

        public Renderer methodDefaultSearch(String av, String pv, String cv, String gv) {
            Renderer render = Renderer.create("#av-value", av);
            render.add("#pv-value", pv);
            render.add("#cv-value", cv);
            render.add("#gv-value", gv);
            return render;
        }

        public Renderer methodScopeSearch(@ContextData(scope = Context.SCOPE_DEFAULT) String av,
                @ContextData(scope = Context.SCOPE_DEFAULT) String pv, @ContextData(scope = Context.SCOPE_GLOBAL) String cv,
                @ContextData(scope = Context.SCOPE_GLOBAL) String gv) {
            Renderer render = Renderer.create("#av-value", av);
            render.add("#pv-value", pv);
            render.add("#cv-value", cv);
            render.add("#gv-value", gv);
            return render;
        }
    }

    public void testMethodDefaultSearch() {
        Context context = Context.getCurrentThreadContext();
        context.setData(Context.SCOPE_DEFAULT, "pv", "pv-value at context");
        context.setData(Context.SCOPE_DEFAULT, "cv", "cv-value at context");
        context.setData(Context.SCOPE_GLOBAL, "cv", "cv-value at global");
        context.setData(Context.SCOPE_GLOBAL, "gv", "gv-value");
        new SimpleCase("Inject_testMethodDefaultSearch.html");
    }

    public void testMethodScopeSearch() {
        Context context = Context.getCurrentThreadContext();
        context.setData(Context.SCOPE_DEFAULT, "av", "av-value at context");
        context.setData(Context.SCOPE_DEFAULT, "pv", "pv-value at context");
        context.setData(Context.SCOPE_DEFAULT, "cv", "cv-value at context");
        context.setData(Context.SCOPE_GLOBAL, "cv", "cv-value at global");
        context.setData(Context.SCOPE_DEFAULT, "gv", "gv-value at context");
        context.setData(Context.SCOPE_GLOBAL, "gv", "gv-value at global");
        new SimpleCase("Inject_testMethodScopeSearch.html");
    }
}
