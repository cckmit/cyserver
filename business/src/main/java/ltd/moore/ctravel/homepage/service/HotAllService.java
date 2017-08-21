package ltd.moore.ctravel.homepage.service;

/**
 * Created by Cocouzx on 2017-6-23 0023.
 */

import com.hdos.platform.common.util.PrimaryKeyUtils;
import ltd.moore.ctravel.experience.model.ExperienceDetailVO;
import ltd.moore.ctravel.homepage.mapper.HotAllMapper;
import ltd.moore.ctravel.homepage.model.HomePageHotVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HotAllService {

    @Autowired
    private HotAllMapper hotAllMapper;
    /**
     * 获取热门类型
     */
    public List<HomePageHotVO> getHotService() {
        return hotAllMapper.getHotService();
    }

    /**
     * 获取热门目的地
     */
    public List<HomePageHotVO> getHotDestinations() {
        return hotAllMapper.getHotDestinations();
    }

    /**
     * 获取热门体验
     * @return
     */
    public List<ExperienceDetailVO> getHotExperience() {
        return hotAllMapper.getHotExperience();
    }

    /**
     * 获取非热门体验
     * @return
     */
    public List<ExperienceDetailVO> getNotHotExperience() {
        return hotAllMapper.getNotHotExperience();
    }
    /**
     * 子页面，添加热门体验
     */
    public void addHotExperience(String idss) {
        HomePageHotVO homePageHotVO = new HomePageHotVO();
        String id = PrimaryKeyUtils.generate(homePageHotVO);
        hotAllMapper.addHotExperience(idss,id);
    }

    /**
     * 删除热门体验
     * @param idss
     */
    public void deleteHotExperience(String idss) {
        hotAllMapper.deleteHotExperienct(idss);
    }

    /**
     * 获取非热门目的地
     * @return
     */
    public List<HomePageHotVO> getNotHotDestinations() {
        return hotAllMapper.getNotHotDestinations();
    }
    /**
     * 增加热门目的地
     */
    public void addHotDestination(String id) {
        HomePageHotVO homePageHotVO = new HomePageHotVO();
        String ids = PrimaryKeyUtils.generate(homePageHotVO);
        hotAllMapper.addHotDestination(id,ids);
    }
    /**
     * 删除热门目的地
     */
    public void deleteHotDestination(String idss) {
        hotAllMapper.deleteHotDestination(idss);
        hotAllMapper.deleteHotDestinationRelative(idss);
    }
    /**
     * 获取非热门服务
     * @return
     */
    public List<HomePageHotVO> getNotHotService() {return hotAllMapper.getNotHotService();
    }
    /**
     * 删除热门服务
     */
    public void deleteHotService(String idss) { hotAllMapper.deleteHotService(idss);
        hotAllMapper.deleteHotServiceRelative(idss);
    }
    /**
     * 增加热门服务
     */
    public void addHotService(String id) {
        HomePageHotVO homePageHotVO = new HomePageHotVO();
        String ids = PrimaryKeyUtils.generate(homePageHotVO);
        hotAllMapper.addHotService(id,ids);
    }

    /**
     * 非热门目的地的体验
     * @param hotDestination
     * @return
     */
    public List<ExperienceDetailVO> getNotHotDestinationsExperience(String hotDestination) {
        return hotAllMapper.getNotHotDestinationsExperience(hotDestination);
    }

    /**
     * 获取热门目的地体验
     * @param hotDestination
     * @return
     */
    public List<ExperienceDetailVO> getHotDestinationExperience(String hotDestination) {
        String temp = hotAllMapper.getHotDestinationById(hotDestination);
        return hotAllMapper.getHotDestinationExperience(hotDestination);
    }

    /**
     * 获取服务的体验
     * @param hotService
     * @return
     */
    public List<ExperienceDetailVO> getHotServiceExperience(String hotService) {
        String temp = hotAllMapper.getHotServiceById(hotService);
        return hotAllMapper.getHotServiceExperienct(hotService);
    }

    /**
     * 添加热门目的地体验
     */
    public void addHotDestinationExperience(String idss, String hotDestination) {
        HomePageHotVO homePageHotVO = new HomePageHotVO();
        String id = PrimaryKeyUtils.generate(homePageHotVO);
        hotAllMapper.addHotDestinationExperience(idss,id,hotDestination);
    }

    /**
     * 删除热门目的地体验
     * @param idss
     */
    public void deleteHotDestinationExperience(String idss) {
        hotAllMapper.deleteHotDestinationExperience(idss);
    }

    /**
     * 删除热门服务体验
     * @param idss
     */
    public void deleteHotServiceExperience(String idss) {
        hotAllMapper.deleteHotServiceExperience(idss);
    }
    /**
     * 非热门服务的体验
     */
    public List<ExperienceDetailVO> getNotHotServiceExperience(String hotDestination) {
        return hotAllMapper.getNotHotServiceExperience(hotDestination);
    }
    /**
     * 添加热门服务体验
     */
    public void addHotServiceExperience(String idss, String hotService) {
        HomePageHotVO homePageHotVO = new HomePageHotVO();
        String id = PrimaryKeyUtils.generate(homePageHotVO);
        hotAllMapper.addHotServiceExperience(idss,id,hotService);
    }
}
