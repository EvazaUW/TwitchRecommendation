const SERVER_ORIGIN = "";

const loginUrl = `${SERVER_ORIGIN}/login`;

// // 展开的写法 - 比较好理解
// // credential object 来自用户输入（username, password）
// export const login = (credential) => {
//   const formData = new FormData(); // FormData() 是后端决定的
//   formData.append("username", credential.username); // formData自带append函数
//   formData.append("password", credential.password);
//
//   const requestStatus = fetch(
//     // 拿到订单号...
//     loginUrl, // 请求的目的
//     {
//       method: "POST",
//       body: formData,
//       credentials: "include", // cookie - 浏览器上一个特别的存储空间
//       // 后端返回response的时候就会带上一个cookie，和一句话：set cookie：xxxx,
//       // 浏览器看到这个 set cookie, 就会把它存下来
//     }
//   );
//   const onSuccess = (response) => {
//     if (response.status !== 204) {
//       throw Error("Fail to log in");
//     }
//   };
//   return requestStatus.then(onSuccess); // 网络请求 回来后（执行）

// 合在一起的写法
export const login = (credential) => {
  const formData = new FormData();
  formData.append("username", credential.username);
  formData.append("password", credential.password);

  return fetch(loginUrl, {
    method: "POST",
    credentials: "include",
    body: formData,
  }).then((response) => {
    if (response.status !== 204) {
      throw Error("Fail to log in");
    }
  });
};

const registerUrl = `${SERVER_ORIGIN}/register`;

export const register = (data) => {
  return fetch(registerUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to register");
    }
  });
};

const logoutUrl = `${SERVER_ORIGIN}/logout`;

export const logout = () => {
  return fetch(logoutUrl, {
    method: "POST",
    credentials: "include",
  }).then((response) => {
    if (response.status !== 204) {
      throw Error("Fail to log out");
    }
  });
};

const topGamesUrl = `${SERVER_ORIGIN}/game`;

export const getTopGames = () => {
  return fetch(topGamesUrl).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to get top games");
    }
    return response.json();
  });
};

const getGameDetailsUrl = `${SERVER_ORIGIN}/game?game_name=`;

const getGameDetails = (gameName) => {
  return fetch(`${getGameDetailsUrl}${gameName}`).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to find the game");
    }
    return response.json();
  });
};

const searchGameByIdUrl = `${SERVER_ORIGIN}/search?game_id=`;

export const searchGameById = (gameId) => {
  return fetch(`${searchGameByIdUrl}${gameId}`).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to find the game");
    }
    return response.json();
  });
};

export const searchGameByName = (gameName) => {
  return getGameDetails(gameName).then((data) => {
    if (data && data[0].id) {
      return searchGameById(data[0].id);
    }
    throw Error("Fail to find the game");
  });
};

const favoriteItemUrl = `${SERVER_ORIGIN}/favorite`;

export const addFavoriteItem = (favItem) => {
  return fetch(favoriteItemUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify({ favorite: favItem }),
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to add favorite item");
    }
  });
};

export const deleteFavoriteItem = (favItem) => {
  return fetch(favoriteItemUrl, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify({ favorite: favItem }),
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to delete favorite item");
    }
  });
};

export const getFavoriteItem = () => {
  return fetch(favoriteItemUrl, {
    credentials: "include",
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to get favorite item");
    }

    return response.json();
  });
};

const getRecommendedItemsUrl = `${SERVER_ORIGIN}/recommendation`;

export const getRecommendations = () => {
  return fetch(getRecommendedItemsUrl, {
    credentials: "include",
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to get recommended item");
    }

    return response.json();
  });
};
