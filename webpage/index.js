class Index extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            
        };


    }


    render() {
        return (    
            <RedditResult 
                getRes={(query) => this.getSentimentRes(query)}

            />
        )
    }

    getSentimentRes(query) {
        var URL = "http://localhost:8080/Stoking/api/getScore/"
        fetch(URL + query)
        .then((response) => {
            console.log(response);
            var sentiment = "";
            if(response <= 40) {
                sentiment = "Not the best idea";
            } else if(response > 40 && response <= 50) {
                sentiment = "On the upside..."
            } else {
                sentiment = "Good Deal!"
            }

            this.setState({
                sentiment : sentiment,
                resValue : response
            });
        }) 
    }
}


var app = document.getElementById('app');

ReactDOM.render(<Index />, app);