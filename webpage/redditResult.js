class RedditResult extends React.Component {
    render() {
        return (
            <div className="container">
                <h1>{this.props.query}</h1>
                <form onSubmit={(e) => this.onSearch(e)}>
                    <input type="text" ref="query"/>
                    <button className="btn-primary" type="submit">Search</button>
                </form>
            </div>
        )
    }

    

    onSearch(e) {
        this.props.getRes(this.props.query);
    }
}
