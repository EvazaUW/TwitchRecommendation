import { Button, Form, Input, message, Modal } from "antd";
import React, { useState } from "react";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { login } from "../utils";

function Login({ onSuccess }) {
  const [displayModal, setDisplayModal] = useState(false);

  const handleCancel = () => {
    setDisplayModal(false);
  };

  // 跳出 login 的弹窗
  const signinOnClick = () => {
    setDisplayModal(true);
  };

  const onFinish = (data) => {
    // login(data) 成功，就做 .then的事；login(data) 不成功，就做 .catch的事
    login(data)
      .then(() => {
        // 登录成功：关闭弹窗 -> 显示成功消息 -> 显示回调函数 onSuccess()改login的状态 （传自 app.js - PageHeader.js - Login.js）
        setDisplayModal(false);
        message.success(`Welcome back`);
        onSuccess();
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  return (
    <>
      <Button
        shape="round"
        onClick={signinOnClick}
        style={{ marginRight: "20px" }}
      >
        Login
      </Button>
      <Modal
        title="Log in"
        visible={displayModal}
        // 用户点x按钮，或者点cancel的时候，需要调用handleCancel这个函数
        // handleCancel函数会执行 setDisplayModal(false)，
        onCancel={handleCancel}
        // 不需要它默认的按钮
        footer={null}
        // 当关闭这个model时销毁（unmount）所有肚子里的东西
        destroyOnClose={true}
      >
        {/* form item: 希望用户填几个空，就有几个form item. */}
        <Form name="normal_login" onFinish={onFinish} preserve={false}>
          <Form.Item
            name="username"
            rules={[{ required: true, message: "Please input your Username!" }]}
          >
            <Input prefix={<UserOutlined />} placeholder="Username" />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[{ required: true, message: "Please input your Password!" }]}
          >
            <Input.Password prefix={<LockOutlined />} placeholder="Password" />
          </Form.Item>
          {/* 最后再加一个form是submit（显示是弹窗底部的Login button），点击button就会提交那个表 */}
          <Form.Item>
            <Button type="primary" htmlType="submit">
              Login
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
}

export default Login;
