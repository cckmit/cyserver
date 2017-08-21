package com.cy.core.alumnicard.dao;

import com.cy.core.alumnicard.entity.AlumniCardExt;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 2/24/17.
 */
public interface AlumniCardExtMapper {
    boolean save(AlumniCardExt alumniCardExt);
    boolean update(AlumniCardExt alumniCardExt);
    List<AlumniCardExt> selectList(String alumniCardId);
    boolean delete(String alumniCardId);

    List<AlumniCardExt> selectListForSign(String signCardId);
    boolean deleteForSign(String signCardId);

}
