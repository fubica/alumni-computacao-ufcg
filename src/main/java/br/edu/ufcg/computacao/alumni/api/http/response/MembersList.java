package br.edu.ufcg.computacao.alumni.api.http.response;

import br.edu.ufcg.computacao.alumni.constants.ApiDocumentation;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class MembersList {
    @ApiModelProperty(example = ApiDocumentation.Model.MEMBERS_LIST)
    private List<String> members;

    public MembersList(List<String> members) {
        this.members = members;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
