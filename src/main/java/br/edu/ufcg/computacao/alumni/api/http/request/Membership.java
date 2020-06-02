package br.edu.ufcg.computacao.alumni.api.http.request;

import br.edu.ufcg.computacao.alumni.Alumni;
import br.edu.ufcg.computacao.alumni.api.http.response.MembersList;
import br.edu.ufcg.computacao.alumni.constants.ApiDocumentation;
import br.edu.ufcg.computacao.alumni.constants.Messages;
import br.edu.ufcg.computacao.alumni.constants.SystemConstants;
import br.edu.ufcg.computacao.alumni.core.service.WhiteList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = Membership.ENDPOINT)
@Api(description = ApiDocumentation.Alumni.API)
public class Membership {

    protected static final String ENDPOINT = SystemConstants.SERVICE_BASE_ENDPOINT + "members";

    private static final Logger LOGGER = Logger.getLogger(Membership.class);

    private Alumni alumniService;

    public Membership() {
        this.alumniService = new WhiteList();
    }

    /**
     * Gets JSON response with a list of members.
     */
    @ApiOperation(value = ApiDocumentation.Alumni.DESCRIPTION)
    @GetMapping
    public ResponseEntity<MembersList> listMembers() {
        if (this.alumniService == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            List<String> membersId = this.alumniService.listMembers();
            MembersList membersList = new MembersList(membersId);
            return new ResponseEntity<MembersList>(membersList, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(Messages.Error.INTERNAL_SERVER_ERROR, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
