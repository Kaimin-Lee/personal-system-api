package com.personal.system.service;

import com.personal.system.entity.Geometry;

import java.util.List;

public interface IGeometryService {
    List<Geometry> getMyHistory();
    void addHistory(Geometry history);
    void clearMyHistory();
}
