package ecm.web.dto;

import java.util.List;

/**
 * Класс для формирования JSON для Dojo Tree
 * @author dkarachurin
 */
public class TreeNode<T> {

    private String name;
    private String id;
    private List<T> children;
    private boolean haveChildren;

    public TreeNode() {
    }

    public TreeNode(String name, String id, List<T> children, boolean haveChildren) {
        this.name = name;
        this.id = id;
        this.children = children;
        this.haveChildren = haveChildren;
    }

    public TreeNode(String name, String id, List<T> children) {
        this.name = name;
        this.id = id;
        this.children = children;
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

}
