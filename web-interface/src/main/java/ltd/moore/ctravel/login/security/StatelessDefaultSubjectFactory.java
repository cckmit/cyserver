package ltd.moore.ctravel.login.security;

/**
 * Created by Cocouzx on 2017-6-28 0028.
 */
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
    public StatelessDefaultSubjectFactory() {
    }

    public Subject createSubject(SubjectContext context) {
        context.setSessionCreationEnabled(true);
        return super.createSubject(context);
    }
}