import React, { useState } from "react";
import { searchGameByName } from "../utils";
import { message, Button, Modal, Form, Input } from "antd";
import { SearchOutlined } from "@ant-design/icons";

// 和 login / register 套路一样
// 有一个按钮。按钮点击弹窗，弹窗可以开/关；弹窗里面有个form, 填完（搜游戏名字）确认，确认之后去调取API
// 点搜索后会更新 onSuccess 状态，然后页面做相应新的渲染

function CustomSearch({ onSuccess }) {
  const [displayModal, setDisplayModal] = useState(false);

  const handleCancel = () => {
    setDisplayModal(false);
  };

  const searchOnClick = () => {
    setDisplayModal(true);
  };

  const onSubmit = (data) => {
    searchGameByName(data.game_name)
      .then((data) => {
        // 搜到了，就关掉弹窗，并调用callback函数（onSuccess），把搜到的信息回传
        setDisplayModal(false);
        onSuccess(data);
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  return (
    <>
      <Button
        shape="round"
        onClick={searchOnClick}
        icon={<SearchOutlined />}
        style={{ marginLeft: "20px", marginTop: "20px" }}
      >
        Custom Search
      </Button>
      <Modal
        title="Search"
        visible={displayModal}
        onCancel={handleCancel}
        footer={null}
      >
        {/* onSubmit 会被 htmlType的submit (Search button)来trigger */}
        <Form name="custom_search" onFinish={onSubmit}>
          <Form.Item
            name="game_name"
            rules={[{ required: true, message: "Please enter a game name" }]}
          >
            <Input placeholder="Game name" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              Search
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
}

export default CustomSearch;
