class Container extends React.Component {

    constructor(props) {
        super(props);
        this.state = { imageUrl: '' };
    }

    render() {
        const rootSytle = { position: "absolute", left: "50%", "margin-left": "-250px" };
        const tipStyle = { 'text-align': 'center' };
        const inputStyle = { width: '500px', };
        const aStyle = {
            color: '#666666',
            margin: '0 10px',
            padding: '0 10px',
            'text-decoration': 'none'
        };
        return (
            <div style={rootSytle}>
                <div style={tipStyle}><p>请输入合法的镜像地址</p></div>
                <div>
                    <span>
                        <input style={inputStyle} type="text" onChange={e => this.setState({ imageUrl: e.target.value })} value={this.state.imageUrl} />
                        <a style={aStyle} href={"image?name=" + encodeURIComponent(this.state.imageUrl)} target="_target" rel="noopener noreferrer">下载镜像</a>
                    </span>
                </div>
            </div>
        );
    }
}

ReactDOM.render(
    <Container />,
    document.getElementById('root')
);
