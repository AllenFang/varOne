import alt from '../alt';
import request from 'superagent';

class NodeAction {

  async fetchNodes(){
    let response = await request.get('http://localhost:8080/Spark_Monitor_Web/rest/nodes')
                                .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch(result);
  }

  async fetchNodeDashBoard(node, selectMetrics){
    if(selectMetrics && selectMetrics.length == 0) selectMetrics = undefined;
    else selectMetrics = selectMetrics.join(",");
    let response = await request.get('http://localhost:8080/Spark_Monitor_Web/rest/nodes/'+node)
                                .query({metrics: selectMetrics})
                                .set('Accept', 'application/json');
    let result = JSON.parse(response.text);
    this.dispatch(result);
  }

}

export default alt.createActions(NodeAction);
