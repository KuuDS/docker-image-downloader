class Query extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoad: false,
      error: null,
      prefix: null,
      images: [],
      value: "",
      prefixValue: "",
    };
    this.searchImage = this.searchImage.bind(this);
  }

  componentDidMount() {
    fetch("/docker/image/prefix")
      .then((res) => res.json())
      .then((res) => this.setState({ prefix: res, prefixValue: res.length > 0 ? res[0] : null, isLoad: true }))
      .catch(e => console.error('connetion error', e))
  }

  searchImage() {
    const uri =
      "/docker/image/tags?name=" +
      encodeURIComponent(this.state.prefixValue + "/" + this.state.value)
    fetch(uri)
      .then((res) => res.json())
      .then(res => this.setState({ images: res.tags }))
      .catch(e => {
        console.log('Connection error', e)
      });
  }

  render() {
    const error = this.state.error;
    const isLoad = this.state.isLoad;
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoad) {
      return <div>Loading...</div>;
    } else {
      return (
        <div>
          <span>
            <select
              value={this.state.prefixValue}
              onChange={(e) => this.setState({ prefixValue: e.target.value })}
            >
              {this.state.prefix.map((value, index) => (
                <option id={"option_" + index} value={value}>{value}</option>
              ))}
            </select>
            <input
              type="text"
              placeholder="请输入微服务地址"
              value={this.state.value}
              onChange={(e) => this.setState({ value: e.target.value })}
            ></input>
            <button onClick={this.searchImage}>查找</button>
          </span>
          <div>
            <ul>
              {this.state.images.map((url, index) => (
                <li id={"li_" + index}>
                  <a id={"a_" + index}
                    href={"image?name=" + encodeURIComponent(url)}
                    target="_target"
                    rel="noopener noreferrer"
                  >
                    {url}
                  </a>
                </li>
              ))}
            </ul>
          </div>
        </div>
      );
    }
  }
}

ReactDOM.render(<Query />, document.getElementById("root"));
