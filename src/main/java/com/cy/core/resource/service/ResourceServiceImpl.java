package com.cy.core.resource.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cy.core.role.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.Tree;
import com.cy.core.resource.dao.ResourceMapper;
import com.cy.core.resource.entity.Resource;
import com.cy.system.ExcelUtil;
import com.cy.system.GetDictionaryInfo;
import com.cy.system.Global;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceMapper resourceMapper;

	public void parseTree(List<Tree> trees, List<Tree> allList) {
		for (Tree tree : allList) {
			if (tree.getPid() == 0) {
				trees.add(tree);
			}
		}
		for (Tree tree : trees) {
			getChildren(tree, allList);
		}
	}

	private void getChildren(Tree tree, List<Tree> allList) {
		List<Tree> children = getChild(tree.getId(), allList);
		if (children != null && children.size() > 0) {
			tree.setChildren(children);
			for (Tree tree2 : children) {
				getChildren(tree2, allList);
			}
		}
	}

	private List<Tree> getChild(long id, List<Tree> allList) {
		List<Tree> children = new ArrayList<Tree>();
		if (allList != null && allList.size() > 0) {
			for (Tree tree : allList) {
				if (tree.getPid() == id) {
					children.add(tree);
				}
			}
		}
		return children;
	}

	public void save(Resource resource) {
		try {
			resourceMapper.save(resource);
			// 刷新内存
			GetDictionaryInfo.getInstance().loadResources();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Resource getById(long id) {
		Resource r =resourceMapper.getById(id);
		return r;
	}

	public void update(Resource resource) {
		try {
			resourceMapper.update(resource);
			// 刷新内存
			GetDictionaryInfo.getInstance().loadResources();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(long id) {
		try {
			List<Resource> allList = resourceMapper.selectAll();
			Resource resource = resourceMapper.getById(id);
			List<Resource> resourceList = new ArrayList<Resource>();
			getCurrentChildren(resource, resourceList, allList);
			List<Long> list = new ArrayList<Long>();
			list.add(id);
			for (Resource resource2 : resourceList) {
				list.add(resource2.getId());
			}
			resourceMapper.deleteRoleAndResource(list);
			resourceMapper.delete(list);
			// 刷新内存
			GetDictionaryInfo.getInstance().loadResources();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void getCurrentChildren(Resource resource,
			List<Resource> resourceList, List<Resource> allList) {
		getResourceChildren(resource, allList);
		getChildren(resource, resourceList);
	}

	private void getResourceChildren(Resource resource, List<Resource> allList) {
		List<Resource> children = getResourceChild(resource.getId(), allList);
		if (children != null && children.size() > 0) {
			resource.setResources(children);
			for (Resource resource2 : children) {
				getResourceChildren(resource2, allList);
			}
		}
	}

	private List<Resource> getResourceChild(long id, List<Resource> allList) {
		List<Resource> children = new ArrayList<Resource>();
		if (allList != null && allList.size() > 0) {
			for (Resource resource : allList) {
				if (resource.getPid() == id) {
					children.add(resource);
				}
			}
		}
		return children;
	}

	private void getChildren(Resource resource, List<Resource> list) {
		if (resource.getResources() != null
				&& resource.getResources().size() > 0) {
			for (Resource resource2 : resource.getResources()) {
				list.add(resource2);
				getChildren(resource2, list);
			}

		}
	}

	public List<Resource> selectAll() {
		return resourceMapper.selectAll();
	}

	public Resource selectByNameOrUrl(Map<String, Object> map) {
		return resourceMapper.selectByNameOrUrl(map);
	}

	/** --校友管理导出excel-- **/
	@Override
	public String export() throws IOException {
		List<Object[]> objects = new ArrayList<Object[]>();
		Object[] o = new Object[9];
		o[0] = "编号";
		o[1] = "资源名称";
		o[2] = "资源编码";
		o[3] = "资源路径";
		o[4] = "父编号";
		o[5] = "图标名称";
		o[6] = "资源类型";
		o[7] = "排序";
		o[8] = "系统";

		objects.add(o);
		List<Resource> list = resourceMapper.selectAllOrderById();
		if (list != null && list.size() > 0) {
			for (Resource resource : list) {
				Object[] o1 = new Object[9];
				o1[0] = String.valueOf(resource.getId());
				o1[1] = resource.getMenuName();
				o1[2] = resource.getName();
				o1[3] = resource.getUrl();
				o1[4] = String.valueOf(resource.getPid());
				o1[5] = resource.getIconCls();
				o1[6] = resource.getType();
				o1[7] = String.valueOf(resource.getSeq());
				o1[8] = String.valueOf(resource.getFlag());

				objects.add(o1);
			}
		}
		return ExcelUtil.exportData(objects);
	}

	public void importData(String url) {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;
			url = savePath + url.replace(saveUrl, "");
			File file = new File(url);
			List<Object[]> list = ExcelUtil.parseExcel(file);
			List<Long> resourceList = new ArrayList<Long>();
			List<Long> listId = new ArrayList<Long>(); //数据库不在excel表中的id
			// 整理数据开始
			List<Resource> allResourceList = resourceMapper.selectAll();
			for (Resource resource : allResourceList) {
				boolean notIn = false;
				long resourceId = resource.getId();
				resourceList.add(resourceId);   //将resource的id放到resourceList,用于后面的删除resource数据库
				
				for (int i = 1; i < list.size(); i++) {
					String idssss = String.valueOf(list.get(i)[0]);
					long imortId =Long.valueOf(idssss);
					 if(resourceId == imortId){
						 notIn = false;
						break;
						 
					 }else{
						 //id不相同则放到 listId里面
						 
						 notIn = true;
					 }
				}if(notIn){
					listId.add(resourceId);
				}
			}
			 //删除对应id的所有数据
			if(listId.size() != 0){
				resourceMapper.deleteRoleAndResource(listId);
			}
			
			resourceMapper.delete(resourceList);
			for(int s = 1;s < list.size(); s ++){
				Resource resource = new Resource();
				resource.setId(Long.valueOf(String.valueOf(list.get(s)[0])));
				resource.setMenuName(String.valueOf(list.get(s)[1]));
				resource.setName(String.valueOf(list.get(s)[2]));
				resource.setUrl(String.valueOf(list.get(s)[3]));
				resource.setPid(Long.valueOf(String.valueOf(list.get(s)[4])));
				resource.setIconCls(String.valueOf(list.get(s)[5]));
				resource.setType(String.valueOf(list.get(s)[6]));
				resource.setSeq(Integer.parseInt(String.valueOf(list.get(s)[7])));
				resource.setFlag(Integer.parseInt(String.valueOf(list.get(s)[8])));
				resourceMapper.save2Id(resource);
				
			}
			GetDictionaryInfo.getInstance().loadResources();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据RoleIds获取角色拥有的资源
	 *
	 * @param roles
	 * @return 资源列表
	 */
	@Override
	public List<Resource> getResourcesByRoles(List<Role> roles) {
		return resourceMapper.getResourcesByRoles(roles);
	}

	/**
	 * 根据RoleIds获取角色拥有的菜单
	 *
	 * @param roles
	 * @return 菜单列表
	 */
	@Override
	public List<Resource> getMenuByRoles(List<Role> roles) {
		return resourceMapper.getMenuByRoles(roles);
	}

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：获取全部菜单
	 *
	 * @return 全部菜单树
	 */
	@Override
	public List<Resource> selectAllMenu() {
		return resourceMapper.selectAllMenu();
	}

	/**
	 * 创建人: jiangling
	 * 创建时间：2016-08-02
	 * 描述：计算菜单下的子菜单数目
	 * @param id
	 * @return 整数
	 */
	@Override
	public int getCountById(long id) {
		return resourceMapper.getCountById(id);
	}

}
