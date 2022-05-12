package grpc;

import io.grpc.Attributes;
import io.grpc.Context;

/**
 * @author djl
 */
public final class Common {

    public static final Attributes.Key<String> TRANS_KEY_CONN_ID = Attributes.Key.create("conn_id");

    public static final Attributes.Key<String> TRANS_KEY_REMOTE_IP = Attributes.Key.create("remote_ip");

    public static final Attributes.Key<Integer> TRANS_KEY_REMOTE_PORT = Attributes.Key.create("remote_port");

    public static final Attributes.Key<Integer> TRANS_KEY_LOCAL_PORT = Attributes.Key.create("local_port");

    public static final Context.Key<String> CONTEXT_KEY_CONN_ID = Context.key("conn_id");

    public static final Context.Key<String> CONTEXT_KEY_CONN_REMOTE_IP = Context.key("remote_ip");

    public static final Context.Key<Integer> CONTEXT_KEY_CONN_REMOTE_PORT = Context.key("remote_port");

    public static final Context.Key<Integer> CONTEXT_KEY_CONN_LOCAL_PORT = Context.key("local_port");
}
