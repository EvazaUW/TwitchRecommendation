import { Menu } from "antd";

const MenuItem = ({ items }) => {
  // ? 的意思是，如果items是undefined, 那不要throw eception, 而是直接返回 undefined;
  // 如果item不是undefined, 正常执行后续的
  return items?.map((item) => {
    // 每个 item 变成一坨 JSX
    return (
      <Menu.Item key={item.id}>
        {/* 如果不写 rel="noopener noreferrer"，跳转到的网站会知道你是从那个网页跳过来的*/}
        <a href={item.url} target="_blank" rel="noopener noreferrer">
          {`${item.broadcaster_name}-${item.title}`}
        </a>
      </Menu.Item>
    );
  });
};

export default MenuItem;
