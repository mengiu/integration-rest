package com.bank.diwa0.service;

import com.bank.diwa0.model.af3a.AF3AINPBST;
import com.bank.diwa0.model.af3a.AF3AOUTBST;

public interface AF3AService extends BaseService {
    void createResource(AF3AINPBST input);
    void updateResource(AF3AINPBST input);
    void deleteResource(AF3AINPBST input);
    AF3AOUTBST retrieveResource(AF3AINPBST input);
}
