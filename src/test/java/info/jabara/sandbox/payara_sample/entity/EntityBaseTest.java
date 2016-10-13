/**
 *
 */
package info.jabara.sandbox.payara_sample.entity;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * @author jabaraster
 */
public class EntityBaseTest {

    /**
     *
     */
    @SuppressWarnings("static-method")
    @Test
    public void _JSONとオブジェクトを双方向に変換できかつ値が変わらないこと() {
        final EUser u = new EUser();
        u.id = Long.valueOf(1);
        u.created = Calendar.getInstance().getTime();
        u.updated = u.created;
        u.name = "hoge";

        final EntityBase.JsonConverter jc = new EntityBase.JsonConverter();

        final EUser c = jc.parse(jc.format(u), EUser.class);

        assertThat(c.getId(), is(u.getId()));
        assertThat(c.getCreated(), is(u.getCreated()));
        assertThat(c.getUpdated(), is(u.getUpdated()));
        assertThat(c.getName(), is(u.getName()));
    }

    /**
     *
     */
    @SuppressWarnings("static-method")
    @Test
    public void _JSONとオブジェクトを双方向に変換できかつ値が変わらないこと_idがnullの場合() {
        final EUser u = new EUser();
        u.created = Calendar.getInstance().getTime();
        u.updated = u.created;
        u.name = "hoge";

        final EntityBase.JsonConverter jc = new EntityBase.JsonConverter();

        final EUser c = jc.parse(jc.format(u), EUser.class);

        assertThat(c.getId(), is(u.getId()));
        assertThat(c.getCreated(), is(u.getCreated()));
        assertThat(c.getUpdated(), is(u.getUpdated()));
        assertThat(c.getName(), is(u.getName()));
    }

    /**
     *
     */
    @SuppressWarnings("static-method")
    @Test
    public void _想定するJSONをオブジェクトに変換できること() {
        final long now = Calendar.getInstance().getTimeInMillis();
        final String name = "hoge";
        final String json = "{\"id\":1,\"name\":\"" + name + "\",\"created\":" + now + ",\"updated\":" + now + "}";
        final EUser u = new EntityBase.JsonConverter().parse(json, EUser.class);

        assertThat(u.getId(), is(new IdValue<EUser>(1)));
        assertThat(u.getName(), is(name));
        assertThat(u.getCreated(), is(new Date(now)));
        assertThat(u.getUpdated(), is(new Date(now)));
    }
}
