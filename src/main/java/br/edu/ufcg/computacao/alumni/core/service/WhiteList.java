package br.edu.ufcg.computacao.alumni.core.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.edu.ufcg.computacao.alumni.Alumni;
import br.edu.ufcg.computacao.alumni.constants.ConfigurationPropertyKeys;
import br.edu.ufcg.computacao.alumni.constants.Messages;
import br.edu.ufcg.computacao.alumni.constants.SystemConstants;
import org.apache.log4j.Logger;

public class WhiteList implements Alumni {
    private static final Logger LOGGER = Logger.getLogger(WhiteList.class.getName());

    private static final String SEPARATOR = ",";

    private List<String> membersList;

    public WhiteList() {
        this.membersList = readMembers();
    }

    /**
     * Read list of Computação@UFCG alumni members from a config file.
     */
    @Override
    public List<String> listMembers() {
        return this.membersList;
    }

    private List<String> readMembers() {
        List<String> membersList = new ArrayList<>();
        try {
            Properties properties = readProperties(SystemConstants.CONF_FILE_PATH);
            String membersListStr = properties.getProperty(ConfigurationPropertyKeys.MEMBERS_LIST_KEY);
            for (String member : membersListStr.split(SEPARATOR)) {
                member = member.trim();
                membersList.add(member);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(Messages.Error.CONFIGURATION_FILE_NOT_FOUND);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error(Messages.Error.ERROR_READING_CONFIGURATION_FILE);
            e.printStackTrace();
        }
        return membersList;
    }

    public static Properties readProperties(String fileName) throws IOException {
        Properties prop = new Properties();
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(fileName);
            prop.load(fileInputStream);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    LOGGER.error(String.format(Messages.Error.UNABLE_TO_CLOSE_FILE_S, fileName), e);
                }
            }
        }
        return prop;
    }
}
