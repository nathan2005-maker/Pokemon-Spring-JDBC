package com.pokemon.pokemonrpg.repository;

import com.pokemon.pokemonrpg.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PokemonRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Esse RowMapper é o que ensina o JDBC a ler as colunas da sua tabela SQLite
    private final RowMapper<Pokemon> pokemonMapper = new RowMapper<Pokemon>() {
        @Override
        public Pokemon mapRow(ResultSet rs, int rowNum) throws SQLException {
            Pokemon p = new Pokemon();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setHp(rs.getInt("hp"));
            p.setAttack(rs.getInt("attack"));
            p.setDefense(rs.getInt("defense"));
            p.setType1(rs.getString("Type1"));
            p.setType2(rs.getString("Type2"));
            p.setSpeed(rs.getInt("speed"));
            p.setSpAttack(rs.getInt("spAttack"));
            p.setSpDefense(rs.getInt("spDefense"));
            return p;
        }
    };

    /**
     * Busca um Pokemon pelo ID para que o main possa usá-lo.
     */
    public Optional<Pokemon> findById(int id) {
        String sql = "SELECT * FROM pokemon WHERE id = ?";
        List<Pokemon> results = jdbcTemplate.query(sql, pokemonMapper, id);
        return results.stream().findFirst();
    }
}