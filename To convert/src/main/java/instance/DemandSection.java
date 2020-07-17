package instance;

public class DemandSection {
    Integer nodeId;
    Integer demande;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getDemande() {
        return demande;
    }

    public void setDemande(Integer demande) {
        this.demande = demande;
    }
    public Integer getDemandeById(int target){
        Integer demandet = null;
        if(target==this.nodeId) {
            demandet = this.demande;
        }
        return demandet;
    }
}
