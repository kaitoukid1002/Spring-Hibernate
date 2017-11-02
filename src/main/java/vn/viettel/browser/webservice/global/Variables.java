package vn.viettel.browser.webservice.global;

import org.elasticsearch.client.transport.TransportClient;

import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Created by quytx on 30/10/17.
 * vn.viettel.browser.webservice.global : NewsWebService
 */
public class Variables {
    public static TransportClient clientEls;
    public static Integer ESPort = 9300;
    public static String[] ESNode =  {"10.240.152.69", "10.240.152.70", "10.240.152.71", "10.240.152.72", "10.240.152.73",
            "10.240.152.74", "10.240.152.75", "10.240.152.76", "10.240.152.77", "10.240.152.78"};
    public static Logger logger;
    public static Integer maxSource = 30;
    public static Integer max_category = 50;
    public static Integer total_filter = 0;
    private static String IP_elasticsearh = "10.240.152.69";
    public static String urlElasticSearch = "http://"+IP_elasticsearh+":9200/br_article_v4/article/_search?q=";
    public static String urlElasticSearchPost = "http://"+IP_elasticsearh+":9200/br_article_v4/article/_search";
    public static String url_elasticsearch_edit = "http://"+IP_elasticsearh+":9200/br_article_v4/article/";
    public static TransportClient client_els;
    public static String command_check_duplitcate = "python /home/quytx/check_duplitcate check_process_duplicate.py";

    public static String[] API_update_tag_ios = {"http://10.240.152.69/update_hot_tags","http://10.240.152.70/update_hot_tags",
            "http://10.240.152.71/update_hot_tags","http://10.240.152.72/update_hot_tags",
            "http://10.240.152.75/update_hot_tags","http://10.240.152.76/update_hot_tags",
            "http://10.240.152.77/update_hot_tags","http://10.240.152.78/update_hot_tags",
            "http://10.240.152.73/update_hot_tags","http://10.240.152.74/update_hot_tags"};

    public static String[] special_character ={"\\t","\\n","\\r","\t","\n","\r","<", ">", "&", "\"", "!", "#", "$", "%", "\'", "(", ")",
            "*", "+", ",", "-", ".", "/", ":", ";", "=", "?", "@", "[",
            "\\", "]", "^", "_", "`", "{", "|", "}", "~"};
    public static HashSet<String> noisy_word = new HashSet<>();

    public static String[]  API_update_tag = {"http://10.240.152.41/update_hot_tags","http://10.240.152.42/update_hot_tags",
            "http://10.240.152.43/update_hot_tags","http://10.240.152.44/update_hot_tags",
            "http://10.240.152.45/update_hot_tags","http://10.240.152.46/update_hot_tags",
            "http://10.240.152.47/update_hot_tags","http://10.240.152.48/update_hot_tags",
            "http://10.240.152.49/update_hot_tags","http://10.240.152.50/update_hot_tags"};
}
