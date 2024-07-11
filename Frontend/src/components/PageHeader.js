import { layout, Row, Col, Button, Layout } from "antd";
import Favorites from "./Favorites";
import Register from "./Register";
import Login from "./Login";
import React from "react";

// import别人的library，如果别人的这几个 library 不是default,就要严格按照别人的，带上花括号
// import 自己的 如从 './Register', 名字可以随意取，如可以写成 Register123

const { Header } = Layout; // 把antD用的这个Layout拿出来赋给App中定义的变量Header

const PageHeader = ({
  loggedIn,
  signoutOnClick,
  signinOnSuccess,
  favoriteItems,
}) => {
  return (
    <Header>
      {/* Row: 把一行分成若干列 */}
      <Row justify="space-between">
        <Col>{loggedIn && <Favorites favoriteItems={favoriteItems} />}</Col>
        <Col>
          {/* 如果 login 为true, 显示 logout button 并在其中调用 signoutOnClick 这个源自App的函数
          （App中要改状态，传下来了这个signoutOnClick，这个函数被PageHeader继续向下传递给了Button, Button时AntD的） */}
          {loggedIn && (
            <Button shape="round" onClick={signoutOnClick}>
              Logout
            </Button>
          )}
          {/* 如果 login 为false, 把 signinOnSuccess 函数传给 login component，
          这句表示如果Login成功了就在 Login 里调用这个 signinOnSuccess 函数  */}
          {!loggedIn && (
            <>
              <Login onSuccess={signinOnSuccess} />
              <Register />
            </>
          )}
        </Col>
      </Row>
    </Header>
  );
};

// default表示导出时，别人用时，能不能乱起名字，用default别人就可以新起名字
export default PageHeader;
