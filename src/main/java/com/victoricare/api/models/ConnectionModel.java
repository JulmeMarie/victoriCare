package com.victoricare.api.models;

import java.util.Date;

import com.victoricare.api.entities.Connection;
import com.victoricare.api.services.IFileService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionModel {

	private Long id;

    private String login;

	private String browser;

	private String token;

	private String ip;

	private Date createAt;

	private Date expireAt;

	private Date deleteAt;

	private Date updateAt;

    private boolean isRemember;

    private  String role;

    private  String right;

    private  UserModel createBy;

    private  Integer deleteBy;

    private  Integer updateBy;

    public static ConnectionModel newInstance() {
    	return new ConnectionModel();
    }

	public ConnectionModel init(IFileService fileService,Connection connection) {
    	if(connection == null) {
    		return null;
    	}
        this.id = connection.getIdConnection();
        this.login = connection.getLoginConnection();
        this.ip = connection.getIpConnection();
        this.browser = connection.getBrowserConnection();
        this.token = connection.getTokenConnection();
        this.role = connection.getRoleConnection();
        this.right = connection.getRightConnection();
        this.isRemember = connection.isRememberConnection();
        this.expireAt = connection.getExpireAtConnection();
        this.createAt = connection.getCreateAtConnection();
        this.deleteAt = connection.getDeleteAtConnection();
        this.updateAt = connection.getUpdateAtConnection();

        this.createBy =  UserModel.newInstance().init(fileService, connection.getCreateByConnection());
		this.deleteBy = connection.getDeleteByConnection() != null ? connection.getDeleteByConnection().getIdUser() : null;
	    this.updateBy = connection.getUpdateByConnection() != null ? connection.getUpdateByConnection().getIdUser() : null;

        return this;
    }
}
