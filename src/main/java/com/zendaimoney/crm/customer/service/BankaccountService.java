package com.zendaimoney.crm.customer.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.DesktopConstant;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.crm.customer.entity.Bankaccount;
import com.zendaimoney.crm.customer.repository.BankaccountDao;
import com.zendaimoney.crm.fund.entity.BusiFunds;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.LoggingHelper;

@Component
@Transactional(readOnly = true)
public class BankaccountService {
	@Autowired
	private BankaccountDao bankaccountDao;

	public Bankaccount getBankaccount(Long id) {
		return bankaccountDao.findOne(id);
	}

	
	public Bankaccount getBankaccountById(Long id) {
		return bankaccountDao.findByIdAndBaValid(id);
	}

	/*
	 * *Jinghr,2013-9-18 16:37:52去除银行卡号空格录入
	 */
	public String replaceBlank(String str) {

		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		return after;
	}

	@Transactional(readOnly = false)
	public Bankaccount saveBankaccount(Bankaccount entity,Long sysuserId) {
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		long staffid = sysuserId; // 员工id
		entity.setBaAccount(replaceBlank(entity.getBaAccount()));
		if (entity.getId() == null) {
			entity.setBaInputDate(now);
			entity.setBaInputId(staffid);
			entity.setBaModifyTime(now);
			entity.setBaModifyId(staffid);
			return bankaccountDao.save(entity);
		} else {
			Bankaccount bank = this.getBankaccount(entity.getId());
			bank.setBaAccount(entity.getBaAccount());
			bank.setBaAccountName(entity.getBaAccountName());
			bank.setBaAccountType(entity.getBaAccountType());
			bank.setBaBankCode(entity.getBaBankCode());
			bank.setBaBankName(entity.getBaBankName());
			bank.setBaBranchName(entity.getBaBranchName());
			bank.setBaValid(entity.getBaValid());
			bank.setBaModifyId(staffid);
			bank.setBaModifyTime(now);
			return bankaccountDao.save(bank);
		}
	}
	
	/**
	 * 保存基金托管账户
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void saveBankaccountForFund(Bankaccount entity,Long sysuserId) {
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		long staffid = sysuserId; // 员工id
		entity.setBaAccount(replaceBlank(entity.getBaAccount()));
		if (entity.getId() == null) {
			entity.setBaInputDate(now);
			entity.setBaInputId(staffid);
			entity.setBaModifyTime(now);
			entity.setBaModifyId(staffid);
		    bankaccountDao.save(entity);
		} else {
			Bankaccount bank = this.getBankaccount(entity.getId());
			bank.setBaAccount(entity.getBaAccount());
			bank.setBaAccountName(entity.getBaAccountName());
			bank.setBaAccountType(entity.getBaAccountType());
			bank.setBaBankCode(entity.getBaBankCode());
			bank.setBaBankName(entity.getBaBankName());
			bank.setBaBranchName(entity.getBaBranchName());
			bank.setBaValid(entity.getBaValid());
			bank.setBaModifyId(staffid);
			bank.setBaModifyTime(now);
			 bankaccountDao.save(bank);
		}
	}
	
	
	@Transactional(readOnly = false)
	public Bankaccount saveBankaccountName(Long customerId) {
		List<Bankaccount> banks = findALlByCustomerid(customerId);
		for (Bankaccount bankaccount : banks) {
			bankaccount.setBaAccountName(bankaccount.getCrmCustomer()
					.getCrName());
			return bankaccountDao.save(bankaccount);
		}
		return null;
	}
	
/**
 * 修改银行开户名
 * @param customerId
 * @return
 */
	@Transactional(readOnly = false)
	public Bankaccount saveBankaccountNameNew(Long customerId) {
		List<Bankaccount> banks = findALlByCustomerid(customerId);
		for (Bankaccount bankaccount : banks) {
			bankaccount.setBaAccountName(bankaccount.getCrmCustomer()
					.getCrName());
			 bankaccountDao.save(bankaccount);//保存修改
		}
		return null;
	}

	@Transactional(readOnly = false)
	public void deleteBankaccount(Long id, SysUser user) {
		Bankaccount bankaccount = bankaccountDao.findOne(id);
		// 记录操作日志信息
		LoggingHelper.createLogging(LoggingType.删除, LoggingSource.客户管理,
				bankaccount.toString(),user);
		bankaccountDao.delete(bankaccount);
	}

	@Transactional(readOnly = false)
	public void deleteBankaccount(Long[] ids) {
		for (Long id : ids) {
			bankaccountDao.delete(id);
		}
	}

	public List<Bankaccount> getAllBankaccount() {
		return (List<Bankaccount>) bankaccountDao.findAll();
	}

	public Page<Bankaccount> getBankaccount(PageRequest pageRequest) {
		return bankaccountDao.findAll(pageRequest);
	}

	public List<Bankaccount> findALlByCustomerid(Long id) {
		return bankaccountDao.findByCrmCustomer(id);
	}
	//根据银行id和客户id查询账户
	public List<Bankaccount> findAllByCusIdAndBankId(Long id,Long bankId) {
		return bankaccountDao.findByCrmCustomerAndBankId(id,bankId);
	}
	public List<Bankaccount> findAllByCustomeridN(Long id) {
		return bankaccountDao.findByCrmCustomerN(id);
	}
}
