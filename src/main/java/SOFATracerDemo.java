
import com.alipay.common.tracer.core.SofaTracer;
import io.opentracing.References;
import io.opentracing.Scope;
import com.alipay.common.tracer.core.span.SofaTracerSpan;

public class SOFATracerDemo {


    private final String tracerType           = "TracerTestService";
    private final String tracerGlobalTagKey   = "tracerkey";
    private final String tracerGlobalTagValue = "tracervalue";
    private SofaTracer   sofaTracer;

    SOFATracerDemo(){
        this.sofaTracer = new SofaTracer.Builder(tracerType).build();
    }

    public void ScopeDemo() {
        SofaTracerSpan parentSpan = (SofaTracerSpan) this.sofaTracer.buildSpan("parent")
                .start();
        try(Scope scope = this.sofaTracer.activateSpan(parentSpan)){
            SofaTracerSpan sonSpan = (SofaTracerSpan) this.sofaTracer.buildSpan("child").start();
            try(Scope sonScope = this.sofaTracer.activateSpan(sonSpan)){
                //sonSpan do something

            }
             catch(Exception e){
                //record
            }
            finally{
                sonSpan.finish();
            }


        }catch (Exception ex){
            //record
        }
        finally {
            parentSpan.finish();
        }
    }

}
