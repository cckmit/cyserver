package ltd.moore.ctravel.hotdestinations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ltd.moore.ctravel.hotdestinations.mapper.HotDestinationsMapper;
import ltd.moore.ctravel.hotdestinations.model.HotDestinationsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class HotDestinationsService {
	
	@Autowired
	private HotDestinationsMapper hotDestinationsMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(HotDestinationsService.class);
}
