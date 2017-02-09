package ecm.web.dto;

import java.util.List;

/**
 * Created by Денис on 09.12.2016.
 */
public class TreeNode<T> {

    private String name;
    private String id;
    private List<T> children;
    private String restUrl;
    private boolean haveChildren;

    public TreeNode() {
    }

    public TreeNode(String name, String id, List<T> children, boolean haveChildren, String restUrl) {
        this.name = name;
        this.id = id;
        this.children = children;
        this.haveChildren = haveChildren;
        this.restUrl = restUrl;
    }

    public TreeNode(String name, String id, List<T> children, String restUrl) {
        this.name = name;
        this.id = id;
        this.children = children;
        this.restUrl = restUrl;
    }

    public TreeNode(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public boolean isHaveChildren() {
        return haveChildren;
    }

    public void setHaveChildren(boolean haveChildren) {
        this.haveChildren = haveChildren;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }
}
