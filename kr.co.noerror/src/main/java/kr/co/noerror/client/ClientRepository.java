package kr.co.noerror.client;

import kr.co.noerror.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Client> clientRowMapper = new RowMapper<>() {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setId(rs.getLong("id"));
            client.setName(rs.getString("name"));
            client.setIndustry(rs.getString("industry"));
            client.setBusinessType(rs.getString("business_type"));
            client.setCode(rs.getString("code"));
            client.setBusinessNumber(rs.getString("business_number"));
            client.setManager(rs.getString("manager"));
            client.setContact(rs.getString("contact"));
            client.setFax(rs.getString("fax"));
            client.setType(rs.getString("type"));
            client.setNote(rs.getString("note"));
            return client;
        }
    };

    public List<Client> findAll() {
        return jdbcTemplate.query("SELECT * FROM client", clientRowMapper);
    }

    public void save(Client client) {
        String sql = """
            INSERT INTO client
            (name, industry, business_type, code, business_number, manager, contact, fax, type, note)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql,
                client.getName(), client.getIndustry(), client.getBusinessType(),
                client.getCode(), client.getBusinessNumber(), client.getManager(),
                client.getContact(), client.getFax(), client.getType(), client.getNote()
        );
    }
}
