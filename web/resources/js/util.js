function updateTree(tree) {

    tree.dndController.selectNone();
    tree.model.store.clearOnClose = true;
    tree._itemNodesMap = {};
    tree.rootNode.state = "UNCHECKED";
    tree.model.childrenCache = null;

    // Destroy the widget
    tree.rootNode.destroyRecursive();

    // Recreate the model, (with the model again)
    tree.model.constructor(tree.model);

    // Rebuild the tree
    tree.postMixInProperties();
    tree._load();
}
