import React from 'react';
import TimePeriodPill from '../commons/time-period-pills'

class JobHeader extends React.Component {

  static propTypes = {
    appId: React.PropTypes.string,
    period: React.PropTypes.string,
    onPeriodSelect: React.PropTypes.func
  }

  render(){
    return(
      <div className="row">
          <div className="col-md-4">
              <h3 className="page-header">{this.props.appId}&nbsp;&nbsp;
                <i className="fa fa-x fa-bar-chart" data-toggle="modal" data-target="#metricsModal"></i></h3>
          </div>
          <div className="col-md-5">
              <h3 className="page-header">
                <TimePeriodPill active={this.props.period} onPeriodSelect={this.props.onPeriodSelect}/>
              </h3>
          </div>
      </div>
    );
  }
}

export default JobHeader;
